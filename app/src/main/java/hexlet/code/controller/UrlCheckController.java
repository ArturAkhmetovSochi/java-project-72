package hexlet.code.controller;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import hexlet.code.dto.urls.UrlChecksPage;
import hexlet.code.repository.UrlCheckRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import static io.javalin.rendering.template.TemplateUtil.model;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.util.NamedRoutes;
import hexlet.code.repository.UrlRepository;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;
import java.net.URI;

public class UrlCheckController {

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var checks = UrlCheckRepository.findByUrlId(id);
        var checksPage = new UrlChecksPage(url);
        checksPage.setFlash(ctx.consumeSessionAttribute("flash"));
        checksPage.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", model("checksPage", checksPage));
    }

    public static void check(Context ctx) throws SQLException, UnirestException {
        var urlId = ctx.pathParamAsClass("id", Long.class).get();
        Url url = UrlRepository.find(urlId)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + urlId + " not found"));
        try {
            URL urlForConnection = new URI(url.getName()).toURL();
            HttpResponse<String> httpResponse = Unirest.get(url.getName()).asString();
            int statusCode = httpResponse.getStatus();
            String responseBody = httpResponse.getBody();
            Document document = Jsoup.parse(responseBody);
            String title = document.title();
            String firstH1 = document.select("h1").text();
            String description = document.select("meta[name=description]").attr("content");
            UrlCheck urlCheck = new UrlCheck();
            urlCheck.setStatusCode(statusCode);
            urlCheck.setH1(firstH1);
            urlCheck.setTitle(title);
            urlCheck.setDescription(description);
            UrlCheckRepository.save(urlCheck, url);
            ctx.sessionAttribute("flash", "Страница успешно проверена");
            ctx.sessionAttribute("flashType", "alert-success");
            ctx.redirect(NamedRoutes.urlPath(urlId));
        } catch (UnirestException | URISyntaxException | MalformedURLException e) {
            ctx.sessionAttribute("flash", "Некорректный адрес");
            ctx.sessionAttribute("flashType", "alert-danger");
            ctx.redirect(NamedRoutes.urlPath(url.getId()));
        }
    }
}

package hexlet.code.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import static io.javalin.rendering.template.TemplateUtil.model;

import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.util.NamedRoutes;
import hexlet.code.repository.UrlRepository;

import java.net.URL;
import java.net.URI;
import java.util.Optional;


public class UrlController {

    public static void index(Context ctx) throws SQLException {
        var urls = UrlRepository.getEntities();
        var page = new UrlsPage(urls);
        ctx.render("urls/index.jte", model("page", page));
    }

    public static void show(Context ctx) throws SQLException {
        var id = ctx.pathParamAsClass("id", Long.class).get();
        var url = UrlRepository.find(id)
                .orElseThrow(() -> new NotFoundResponse("Entity with id = " + id + " not found"));
        var page = new UrlPage(url);
        ctx.render("urls/show.jte", model("page", page));
    }


    public static void create(Context ctx) throws SQLException,  IllegalArgumentException  {
        var name = ctx.formParam("url");

        try {
            URL absoluteUrl = new URI(name).toURL();
            String schema = absoluteUrl.toURI().getScheme();
            String authority = absoluteUrl.toURI().getAuthority();
            Url url = new Url(schema + "://" + authority);
            Optional<Url> foundedUrl = UrlRepository.findByName(url.getName());
            if (foundedUrl.isEmpty()) {
                UrlRepository.save(url);
                ctx.sessionAttribute("flash", "Страница успешно добавлена");
                ctx.sessionAttribute("flashType", "alert-success");
            } else {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flashType", "alert-info");
            }
            ctx.redirect(NamedRoutes.urlsPath());
        } catch (URISyntaxException | IllegalArgumentException | IOException e) {
            ctx.sessionAttribute("flash", "Некорректный URL");
            ctx.sessionAttribute("flashType", "alert-danger");
            ctx.redirect(NamedRoutes.rootPath());
        }
    }

}

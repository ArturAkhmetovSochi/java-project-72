@import hexlet.code.util.NamedRoutes
@import hexlet.code.util.Formatter
@import hexlet.code.dto.urls.UrlPage
@param UrlPage page

@template.layout.page(
    page = page,
    content = @`

         @if(page.getFlash() != null)
                    <div class="rounded-0 m-0 alert alert-dismissible fade show ${page.getFlashType()}" role="alert">
                        <p class="m-0">${page.getFlash()}</p>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                @endif

                 <nav class="navbar navbar-expand-lg navbar-dark bg-info">
                        <div class="container-fluid">
                         <a class="navbar-brand" href="/">Анализатор страниц</a>
                          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                               aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                                   <span class="navbar-toggler-icon"></span>
                          </button>
                      <div class="collapse navbar-collapse" id="navbarNav">
                       <div class="navbar-nav">
                        <a class="nav-link" href="/">Главная</a>
                         <a class="nav-link" href="/urls">Сайты</a>
                          </div>
                          </div>
                          </div>
                           </nav>

        <div class="container-lg mt-5">
                    <h1>Сайт:${page.getUrl().getName()}</h1>
                    <table class="table table-bordered table-hover mt-3">
                        <tbody>
                        <tr>
                            <td>ID</td>
                            <td>${page.getUrl().getId()}</td>
                        </tr>
                        <tr>
                            <td>Имя</td>
                            <td>${page.getUrl().getName()}</td>
                        </tr>
                        <tr>
                            <td>Дата создания</td>
                            <td>${page.getUrl().getCreatedAt().toLocalDateTime().format(Formatter.formatterCreatedAt)}</td>
                        </tr>
                        </tbody>
                    </table>

                    <h2 class="mt-5">Проверки</h2>
                    <form method="post" action="/urls/${page.getUrl().getId()}/checks">
                        <button type="submit" class="btn btn-primary">Запустить проверку</button>
                    </form>

                    <table class="table table-bordered table-hover mt-3">
                        <thead>
                            <tr><th class="col-1">ID</th>
                                <th class="col-1">Код ответа</th>
                                <th>title</th>
                                <th>h1</th>
                                <th>description</th>
                                <th class="col-2">Дата проверки</th>
                            </tr>
                        </thead>
                        <tbody>
                            @if(page.getUrl().getChecks().isEmpty())

                            @else
                                @for(var check : page.getUrl().getChecks())
                                    <tr>
                                        <td>
                                            ${check.getId()}
                                        </td>
                                        <td>
                                            ${check.getStatusCode()}
                                        </td>
                                        <td>
                                            ${check.getTitle()}
                                        </td>
                                        <td>
                                            ${check.getH1()}
                                        </td>
                                        <td>
                                            ${check.getDescription()}
                                        </td>
                                        <td>
                                            ${check.getCreatedAt().toLocalDateTime().format(Formatter.formatterCreatedAt)}
                                        </td>
                                    </tr>
                                @endfor
                            @endif
                        </tbody>
                    </table>
                </div>
            </section>
    `
)
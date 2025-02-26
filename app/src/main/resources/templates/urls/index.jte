@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
@import hexlet.code.util.Formatter
@param UrlsPage page

@template.layout.page(
    page = page,
    content = @`
     @if(page.getFlash() != null)
                <div class="rounded-0 m-0 alert alert-dismissible fade show ${page.getFlashType()}" role="alert">
                    <p class="m-0">${page.getFlash()}</p>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            @endif

     <body class="d-flex flex-column min-vh-100">
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

           <h1>Сайты</h1>

           <main class="flex-grow-1">

                       <section>

                   <div class="container-lg mt-5">
                       <table class="table table-bordered table-hover mt-3">
                           <thead>
                           <tr>
                               <th class="col-1">ID</th>
                               <th>Имя</th>
                               <th class="col-2">Последняя проверка</th>
                               <th class="col-1">Код ответа</th>
                           </tr>
                           </thead>

                            <tbody>
                            @for(var url : page.getUrls())
                                <tr>
                                    <td>
                                         ${url.getId()}
                                    </td>
                                    <td>
                                        <a href="/urls/${url.getId()}">${url.getName()}</a>
                                    </td>
                                @if(page.getChecks().isEmpty())
                                    <td>
                                    </td>
                                    <td>
                                    </td>
                                    @endif
                                    @for(var check : page.getChecks())
                                       @if(check.getUrlId() == url.getId())
                                          <td>
                                            ${check.getCreatedAt().toLocalDateTime().format(Formatter.formatterCreatedAt)}
                                          </td>
                                          <td>
                                            ${check.getStatusCode()}
                                          </td>
                                          @endif
                                          @endfor
                                    </tr>
                            @endfor
                </tbody>
            </table>
        </div>

            </section>
        </main>
    `
)

@import hexlet.code.util.NamedRoutes
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
        <h1>${page.getUrl().getName()}</h1>
    `
)
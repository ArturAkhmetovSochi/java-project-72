@import hexlet.code.util.NamedRoutes
@import hexlet.code.dto.urls.UrlsPage
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
        <h1>Urls List</h1>
        @if(page.getUrls().isEmpty())
            <p>Urls list is empty</p>
        @else
            @for(var url : page.getUrls())
               <div>
                   <h2>
                       <a href="/urls/${(url.getId())}">${url.getName()}</a>
                   </h2>
               </div>
            @endfor
        @endif
    `
)

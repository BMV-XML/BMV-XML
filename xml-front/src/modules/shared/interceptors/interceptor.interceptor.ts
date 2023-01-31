import {Injectable} from '@angular/core'
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor, HttpHeaders
} from '@angular/common/http'
import {Observable} from 'rxjs'

@Injectable()
export class Interceptor implements HttpInterceptor {
    intercept(
        req: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        //const item = localStorage.getItem('token')
        //if (item) {
        //let u, p: string = ''
        //if (localStorage.getItem('username') !== null)
        //  u = localStorage.getItem('username')
        //if (localStorage.getItem('password'))
        //  p = localStorage.getItem('password').toString()
        //let u : string = ((localStorage.getItem('username') !== null) ? '' : localStorage.getItem('username'));

        if (!req.headers.has('myattr')) {

            const cloned = req.clone({
                headers: new HttpHeaders()
                    .append('Content-Type', 'application/xml')
                    .append('Accept', 'application/xml')
                    .append('username', String(localStorage.getItem('username')))
                    .append('password', String(localStorage.getItem('password')))
            })

            console.log(cloned)
            return next.handle(cloned)
        } else {
            const cloned = req.clone()
            console.log("multipart")
            console.log(cloned)
            return next.handle(cloned)
        }
    }
}

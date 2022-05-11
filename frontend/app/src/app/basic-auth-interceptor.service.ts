import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthHtppInterceptorService implements HttpInterceptor {

  constructor() { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (localStorage.getItem('username') && localStorage.getItem('token')) {

      let token: any = '';
      if ( localStorage.getItem('token') != null ) {
        token = localStorage.getItem('token');
      }
      const headers = new HttpHeaders({
        Authorization: token
      });
      req = req.clone({ headers });
    }

    return next.handle(req);

  }
}

import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AuthService } from '@/service/auth.service';

@Injectable()
export class MockInterceptor implements HttpInterceptor {
    private headers = new HttpHeaders({"Content-Type": "application/json"});
  
    constructor(private authenticationService: AuthService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        if (request.url.endsWith('user/login') && (request.method == 'POST')) {
          return of(new HttpResponse<any>({ body: "THIS_IS_A_TOKEN", status: 200, headers: this.headers }));
        }
        if (request.url.endsWith('user/info')) {
          return of(new HttpResponse<any>({ body: { email: "info@example.org" }, status: 200, headers: this.headers }));
        }
    
    

        return next.handle(request);
    }
}

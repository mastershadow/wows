import { User } from '@/model/User';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'environment/environment';
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RestApiService {
  private baseUrl: string = "";
  private apiPrefix: string = "/api/v1"

  constructor(
    private http: HttpClient
  ) {
    this.baseUrl = environment.apiBaseUrl + this.apiPrefix;
  }

  login(username: string, password: string): Observable<string> {
    const formData = new FormData();
    formData.append("username", username);
    formData.append("password", password);
    return this.http.post(`${this.baseUrl}/user/login`, formData, { responseType: 'text' });
  }

  info(token: string): Observable<User> {
    return this.http.get<any>(`${this.baseUrl}/user/info`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  logout(): Observable<boolean> {
    return of(true);
  }


}

import { Injectable } from '@angular/core';
import { RestApiService } from './rest-api.service';
import { BehaviorSubject, map, mergeMap, Observable } from 'rxjs';
import { User } from '../model/User';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject: BehaviorSubject<User>;


  constructor(private api: RestApiService) {
    this.currentUserSubject = new BehaviorSubject(this.getUserFromStorageOrDefault());
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return this.currentUserValue.token != null;
  }

  login(username: string, password: string): Observable<User> {
    return this.api.login(username, password)
      .pipe(
        mergeMap(token => {
          return this.api.info(token)
            .pipe(map(user => {
              user.token = token;
              return user;
            }));
        }),
        map(user => {
          sessionStorage.setItem("user", JSON.stringify(user));
          this.currentUserSubject.next(user);
          return user;
        }));
  }

  logout(): Observable<boolean> {
    // remove user from local storage and set current user to null
    return this.api.logout().pipe(map(v => {
      this.currentUserSubject.next(this.unloggedUser());
      return v;
    }));
  }

  unloggedUser(): User {
    return {} as User;
  }

  getUserFromStorageOrDefault(): User {
    const data = sessionStorage.getItem("user");
    if (data) {
      return JSON.parse(data) as User;
    }
    return this.unloggedUser();

  }


}

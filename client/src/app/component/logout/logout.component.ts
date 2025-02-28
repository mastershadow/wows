import { AuthService } from '@/service/auth.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.scss'
})
export class LogoutComponent implements OnInit {

    constructor(private auth: AuthService, private router: Router) {
    }

    ngOnInit(): void {
      if (this.auth.isLoggedIn()) {
        this.auth.logout().subscribe(b => {
          this.router.navigate(['login']);
        })
      }
    }


}

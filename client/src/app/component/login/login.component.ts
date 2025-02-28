import { Component, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormControl, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar } from '@angular/material/snack-bar';

import { AuthService } from '@/service/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  imports: [FormsModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, MatButtonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  private _snackBar = inject(MatSnackBar);
  private readonly afterLogin = ['dashboard'];

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  passwordFormControl = new FormControl('', [Validators.required]);

  constructor(private auth: AuthService, private router: Router) {
    if (this.auth.isLoggedIn()) {
      this.router.navigate(this.afterLogin);
    }
  }

  onLoginClick(): void {
    if (this.emailFormControl.valid && this.passwordFormControl.valid) {
      this.auth.login(this.emailFormControl.value!, this.passwordFormControl.value!).subscribe(u => {
        this.router.navigate(this.afterLogin);
      }, e => {
        this._snackBar.open("Error logging in");
      });
    }
  }
}

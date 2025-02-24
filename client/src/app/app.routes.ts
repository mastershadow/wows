import { CanActivateFn, Routes } from '@angular/router';
import { AuthService } from './service/auth.service';
import { inject } from '@angular/core';
import { LoginComponent } from '@/component/login/login.component';
import { LogoutComponent } from '@/component/logout/logout.component';
import { BalanceComponent } from '@/component/balance/balance.component';
import { TransactionsComponent } from '@/component/transactions/transactions.component';
import { AssetsComponent } from '@/component/assets/assets.component';
import { TradesComponent } from './component/trades/trades.component';

const isLoggedGuard: CanActivateFn = (route, state) => { return inject(AuthService).isLoggedIn() };

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent, canActivate: [ isLoggedGuard ] },
  { path: 'balance', component: BalanceComponent, canActivate: [ isLoggedGuard ] },
  { path: 'trades', component: TradesComponent, canActivate: [ isLoggedGuard ] },
  { path: 'transactions', component: TransactionsComponent, canActivate: [ isLoggedGuard ] },
  { path: 'assets', component: AssetsComponent, canActivate: [ isLoggedGuard ] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];

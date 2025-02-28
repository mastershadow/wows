import { CanActivateFn, Routes } from '@angular/router';
import { AuthService } from './service/auth.service';
import { inject } from '@angular/core';
import { LoginComponent } from '@/component/login/login.component';
import { LogoutComponent } from '@/component/logout/logout.component';
import { TransactionsComponent } from '@/component/transactions/transactions.component';
import { TradesComponent } from './component/trades/trades.component';
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { TickersComponent } from './component/tickers/tickers.component';
import { SettingsComponent } from './component/settings/settings.component';

const isLoggedGuard: CanActivateFn = (route, state) => { return inject(AuthService).isLoggedIn() };

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'logout', component: LogoutComponent, canActivate: [ isLoggedGuard ] },
  { path: 'dashboard', component: DashboardComponent, canActivate: [ isLoggedGuard ] },
  { path: 'trades', component: TradesComponent, canActivate: [ isLoggedGuard ] },
  { path: 'transactions', component: TransactionsComponent, canActivate: [ isLoggedGuard ] },
  { path: 'tickers', component: TickersComponent, canActivate: [ isLoggedGuard ] },
  { path: 'settings', component: SettingsComponent, canActivate: [ isLoggedGuard ] },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' },
];

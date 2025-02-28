import { Component } from '@angular/core';
import { BalanceComponent } from "../balance/balance.component";
import { AssetsComponent } from "../assets/assets.component";

@Component({
  selector: 'app-dashboard',
  imports: [BalanceComponent, AssetsComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

}

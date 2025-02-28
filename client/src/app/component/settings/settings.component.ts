import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
export interface SettingsEntry {
  key: string;
  value: string;
}

const ELEMENT_DATA: SettingsEntry[] = [
  {key: '1', value: 'Hydrogen'},
  {key: '2', value: 'Helium'},
  {key: '3', value: 'Lithium'},
  {key: '4', value: 'Beryllium'},
  {key: '5', value: 'Boron'},
  {key: '6', value: 'Carbon'},
  {key: '7', value: 'Nitrogen'},
  {key: '8', value: 'Oxygen'},
  {key: '9', value: 'Fluorine'},
  {key: '10', value: 'Neon'}
];
@Component({
  selector: 'app-settings',
  imports: [MatTableModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {
  displayedColumns: string[] = ['key', 'value'];
  dataSource = ELEMENT_DATA;

}

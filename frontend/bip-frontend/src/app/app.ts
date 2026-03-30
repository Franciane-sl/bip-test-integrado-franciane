import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { BeneficioFormComponent } from './components/beneficio-form/beneficio-form';
import { TransferenciaComponent } from './components/transferencia.component/transferencia.component';

@Component({
  selector: 'app-root',
  imports: [BeneficioFormComponent, TransferenciaComponent],
  templateUrl: './app.html',
  styleUrls: ['./app.css']
})
export class App {
  protected readonly title = signal('bip-frontend');
}
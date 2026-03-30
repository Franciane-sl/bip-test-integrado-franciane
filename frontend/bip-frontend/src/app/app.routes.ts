import { Routes } from '@angular/router';
import { BeneficioFormComponent } from './components/beneficio-form/beneficio-form';
import { TransferenciaComponent } from './components/transferencia.component/transferencia.component';

export const routes: Routes = [
  { path: '', component: BeneficioFormComponent }, 
  { path: 'transferencia', component: TransferenciaComponent }, 
];
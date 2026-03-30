import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BeneficioService } from '../../services/beneficioService';

@Component({
  selector: 'app-transferencia',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './transferencia.component.html',
  styleUrls: ['./transferencia.component.css']
})
export class TransferenciaComponent implements OnInit {
  form!: FormGroup;

  constructor(private fb: FormBuilder, private beneficioService: BeneficioService) {}

  ngOnInit() {
    this.form = this.fb.group({
      fromId: [null, Validators.required],
      toId: [null, Validators.required],
      amount: [0, [Validators.required, Validators.min(0.01)]]
    });
  }

  submit() {
    if (this.form.invalid) return;

    const { fromId, toId, amount } = this.form.value;

    if (fromId === toId) {
      alert('Não é possível transferir para o mesmo benefício.');
      return;
    }

    this.beneficioService.transferir(fromId, toId, amount).subscribe({
      next: () => {
        alert(`Transferência de R$${amount} realizada com sucesso!`);
        this.form.reset({ amount: 0 });
      },
      error: (err: any) => {
        console.error(err);
        alert('Erro ao realizar transferência. Verifique o console.');
      }
    });
  }
}
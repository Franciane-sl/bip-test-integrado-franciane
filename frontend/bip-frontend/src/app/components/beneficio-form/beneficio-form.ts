import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BeneficioService, Beneficio } from '../../services/beneficioService';

@Component({
  selector: 'app-beneficio-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './beneficio-form.html',
  styleUrls: ['./beneficio-form.css']
})
export class BeneficioFormComponent implements OnInit {
  form!: FormGroup;

  constructor(private fb: FormBuilder, private beneficioService: BeneficioService) {}

  ngOnInit() {
   
    this.form = this.fb.group({
      nome: ['', Validators.required],
      descricao: [''],
      valor: [0, [Validators.required, Validators.min(0.01)]],
      ativo: [true]
    });
  }

  submit() {
    if (this.form.invalid) return;

    const beneficio: Beneficio = this.form.value;

    this.beneficioService.criar(beneficio).subscribe({
      next: (res: Beneficio) => {
        alert('Benefício criado com sucesso!');
        this.form.reset({ ativo: true, valor: 0 }); 
      },
      error: (err: any) => {
        console.error(err);
        alert('Erro ao criar benefício. Verifique o console.');
      }
    });
  }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Beneficio {
  nome: string;
  descricao: string;
  valor: number;
  ativo: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class BeneficioService {
  private baseUrl = 'http://localhost:8080/api/v1/beneficios';

  constructor(private http: HttpClient) {}

  listar(): Observable<Beneficio[]> {
    return this.http.get<Beneficio[]>(this.baseUrl);
  }

  buscarPorId(id: number): Observable<Beneficio> {
    return this.http.get<Beneficio>(`${this.baseUrl}/${id}`);
  }

  criar(beneficio: Beneficio): Observable<Beneficio> {
    return this.http.post<Beneficio>(this.baseUrl, beneficio);
  }

  atualizar(id: number, beneficio: Beneficio): Observable<Beneficio> {
    return this.http.put<Beneficio>(`${this.baseUrl}/${id}`, beneficio);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  transferir(fromId: number, toId: number, amount: number): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/transfer?fromId=${fromId}&toId=${toId}&amount=${amount}`, {});
  }
}
import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../environments/environment';

@Component({
  selector: 'app-root',
  imports: [],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  apiBaseUrl = environment.apiBaseUrl;
  healthStatus = 'Checking...';
  message = '';

  constructor(private readonly http: HttpClient) {}

  ngOnInit(): void {
    this.checkHealth();
  }

  checkHealth(): void {
    this.healthStatus = 'Checking...';
    this.message = '';

    this.http.get<{ status?: string; message?: string }>(`${this.apiBaseUrl}/health`).subscribe({
      next: (res) => {
        this.healthStatus = res.status ?? 'ok';
        this.message = res.message ?? 'API reachable';
      },
      error: () => {
        this.healthStatus = 'error';
        this.message = 'Could not connect to backend API';
      }
    });
  }
}

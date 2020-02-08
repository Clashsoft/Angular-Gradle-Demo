import {Component, OnInit} from '@angular/core';

import {HttpClient} from '@angular/common/http';
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'angular-gradle-demo';
  sparkMessage = 'fetching spark message...';

  constructor(
    private http: HttpClient,
  ) {
  }

  ngOnInit(): void {
    this.http.get(environment.apiURL + '/hello', {responseType: 'text'})
      .subscribe(message => this.sparkMessage = message);
  }
}

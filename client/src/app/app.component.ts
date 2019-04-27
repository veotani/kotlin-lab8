import { Component } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  collectionUrl = 'http://localhost:8080/Gradle___HomeWork_8_1_0_SNAPSHOT_war__exploded_/hello'
  recivedData = ''
  errorMessage = ''
  lastAccessedField = ''

  constructor(private http: HttpClient)
  {
    //
  }

  getValueByKey(fieldToSearch)
  {
    this.lastAccessedField = fieldToSearch

    const params = new HttpParams({fromString: `requestedDataKey=${fieldToSearch}`})

    this.http.get<string>(this.collectionUrl, {responseType: 'json', params})
      .subscribe(
        data => this.recivedData = data.toString(),
        error => this.errorMessage = error.message
      )
  }

  setValueByKey(newValue)
  {
    if (isNaN(parseInt(newValue))){
      console.log(parseInt(newValue))
      this.errorMessage = 'You have to provide number to change the value'
      return
    }
    
    let dataToSend = {
      'dataKey': this.lastAccessedField,
      'dataNewValue': newValue
    }

    let headers = new HttpHeaders({ 'Content-Type': 'application/json' })
    headers.append('Accept', 'application/json, text/csv');
    headers.append('X-Requested-With', 'XMLHttpRequest');

    this.http.post<string>(this.collectionUrl, dataToSend, { headers })
      .subscribe(
        data => this.errorMessage = data.toString(),
        error => this.errorMessage = error.message
      )
  }

}

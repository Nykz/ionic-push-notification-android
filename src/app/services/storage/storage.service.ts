import { Injectable } from '@angular/core';
import { Preferences } from '@capacitor/preferences';
import { from, Observable } from 'rxjs';

export const APP_TOKEN = 'app_token';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  setStorage(key: string, value: any) {
    Preferences.set({key: key, value: value});
  }

  getStorage(key: string): any {
    // Preferences.migrate();
    return Preferences.get({key: key});
  }

  removeStorage(key: string) {
    Preferences.remove({key: key});
  }

  clearStorage() {
    Preferences.clear();
  }

  getToken(): Observable<any> {
    return from(this.getStorage(APP_TOKEN));    
  }
  
}

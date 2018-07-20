import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";

@Injectable()
export class TranslationNotifierService {

  // Sources
  private _translationsLoaded = new BehaviorSubject<boolean>(false);

  // Observable sources
  public translationsLoaded$ = this._translationsLoaded.asObservable();

  public publishTranslationsLoaded() {
    this._translationsLoaded.next(true);
  }
}

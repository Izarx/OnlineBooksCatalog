import {Component} from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    title: string;

    constructor() {
    }

    setTitle(event: any) {
        this.title = event.title;
    }
}

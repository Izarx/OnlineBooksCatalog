import {Component, Input} from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {

    title: string = 'Home'

    constructor() {
    }

    setTitle(title: string) {
        this.title = title
    }
}

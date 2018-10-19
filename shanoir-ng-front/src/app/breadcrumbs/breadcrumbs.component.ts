import { Component } from '@angular/core';
import { BreadcrumbsService, Step } from './breadcrumbs.service';
import { Router } from '@angular/router';

@Component({
    selector: 'breadcrumbs',
    templateUrl: 'breadcrumbs.component.html',
    styleUrls: ['breadcrumbs.component.css']
})

export class BreadcrumbsComponent {

    constructor(
        private service: BreadcrumbsService,
        private router: Router) { 
    }

    get steps(): Step[] {
        return this.service.steps;
    }

    clickStep(index: number) {
        if (index < this.service.steps.length - 1)
            this.service.goToStep(index);
    }

    private get displayHome(): boolean {
        let foundHome: boolean = false;
        for (let i=this.service.currentStepIndex; i>=0; i--) {
            if (this.service.steps[i].route == '/home') foundHome = true;
        }
        return !foundHome;
    }
}
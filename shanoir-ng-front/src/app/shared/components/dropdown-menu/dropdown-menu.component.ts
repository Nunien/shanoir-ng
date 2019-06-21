/**
 * Shanoir NG - Import, manage and share neuroimaging data
 * Copyright (C) 2009-2019 Inria - https://www.inria.fr/
 * Contact us on https://project.inria.fr/shanoir/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/gpl-3.0.html
 */

import { Component, Input, ContentChildren, forwardRef, QueryList, ViewChild, ElementRef, HostBinding, Renderer } from '@angular/core';
import { style, animate, transition, trigger } from '@angular/core';
import { MenuItemComponent } from './menu-item/menu-item.component'
import { Observable } from 'rxjs';


export const animDur: number = 100;

@Component({
    selector: 'dropdown-menu',
    templateUrl: 'dropdown-menu.component.html',
    styleUrls: ['dropdown-menu.component.css'],
    animations: [trigger('slideDown', [
        transition(
            ':enter', [
                style({ height: 0 }),
                animate(animDur + 'ms ease-in-out', style({ height: '*', 'padding-bottom': '*' }))
            ]
        ),
        transition(
            ':leave', [
                style({ height: '*' }),
                animate(animDur + 'ms ease-in-out', style({ height: 0, 'padding-bottom': '0' }))
            ]
        )
    ])]
})
export class DropdownMenuComponent {

    @Input() label: string;
    @Input() link: string;
    @ContentChildren(forwardRef(() => MenuItemComponent)) itemMenus: QueryList<MenuItemComponent>;
    @Input() boolVar: boolean;
    @ViewChild('container') container: ElementRef;
    @Input() mode: "top" | "tree";

    public opened: boolean = true;
    public parent: any;
    public hasChildren: boolean = true;
    public overflow: boolean = false;
    public init: boolean = false;

    private static documentListenerInit = false;
    private static openedMenus: Set<DropdownMenuComponent>; // every opened menu in the document (upgrade idea : named groups of menu)

    constructor(public elementRef: ElementRef, private renderer: Renderer) {
        this.mode = "top";
        DropdownMenuComponent.openedMenus = new Set<DropdownMenuComponent>();

        if (!DropdownMenuComponent.documentListenerInit) {
            DropdownMenuComponent.documentListenerInit = true;
            document.addEventListener('click', DropdownMenuComponent.closeAll.bind(this));
        }
    }

    ngAfterViewInit() {
        this.itemMenus.forEach((itemMenu: MenuItemComponent) => {
            itemMenu.siblings = this.itemMenus;
            itemMenu.parent = this;
        });

        let subscription = Observable.timer(0, 100).subscribe(t => {
            this.hasChildren = this.itemMenus.length > 0;
            this.opened = false;
            this.overflow = true;
            this.init = true;
            subscription.unsubscribe();
        });

        this.renderer.setElementClass(this.elementRef.nativeElement, this.mode + "-mode", true);
    }

    public open(event: Event) {
        if (DropdownMenuComponent.openedMenus.size > 0) {
            event.stopPropagation();
            DropdownMenuComponent.closeAll(event, () => {
                this.openAction();
            });
        } else {
            this.openAction();
        }
    }

    private openAction() {
        this.opened = true;
        DropdownMenuComponent.openedMenus.add(this);
        setTimeout(() => this.overflow = false, animDur);
    }

    public close(callback: () => void = () => { }) {
        if (this.hasChildren && this.opened) {
            this.closeChildren(() => {
                this.overflow = true;
                this.opened = false;
                DropdownMenuComponent.openedMenus.delete(this);
                setTimeout(callback, animDur);
            });
        } else {
            callback();
        }
    }

    public closeChildren(callback: () => void = () => { }) {
        let menusToClose: MenuItemComponent[] = [];
        this.itemMenus.forEach((itemMenu, index) => {
            if (index != 0 && itemMenu.hasChildren && itemMenu.opened) // REMOVE index != 0 WHEN BUG FIXED
                menusToClose.push(itemMenu);
        });
        let subMenusRemaining: number = menusToClose.length;
        if (subMenusRemaining == 0) {
            callback();
            return;
        } else {
            for (let itemMenu of menusToClose) {
                itemMenu.close(() => {
                    subMenusRemaining--;
                    if (subMenusRemaining == 0) {
                        callback();
                    }
                });
            }
        }
    }

    public toggle(event: Event) {
        if (this.opened) this.close();
        else this.open(event);
    }

    public click() {
        if (this.link != undefined || this.boolVar == undefined) {
            this.cascadingClose();
        }
    }

    public cascadingClose() {
        if (this.parent != undefined)
            this.parent.cascadingClose();
    }

    public static closeAll = (event: Event, callback: () => void = () => { }) => {
        let remains: number = DropdownMenuComponent.openedMenus.size;
        DropdownMenuComponent.openedMenus.forEach((menu) => {
            if (!menu.container.nativeElement.contains(event.target)) {
                menu.close(() => {
                    remains--;
                    if (remains == 0) {
                        callback();
                    }
                });
            } else {
                remains--;
            }
        });
    }

    public getMode(): "top" | "tree" {
        if (this.mode == "top" || this.mode == "tree") {
            return this.mode;
        } else {
            return "top";
        }
    }
}
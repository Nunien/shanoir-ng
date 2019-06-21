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

import { Component, ViewChild } from '@angular/core';

import { BrowserPaginEntityListComponent } from '../../shared/components/entity/entity-list.browser.component.abstract';
import { TableComponent } from '../../shared/components/table/table.component';
import { Subject } from '../shared/subject.model';
import { SubjectService } from '../shared/subject.service';

@Component({
    selector: 'subject-list',
    templateUrl: 'subject-list.component.html',
    styleUrls: ['subject-list.component.css']
})

export class SubjectListComponent extends BrowserPaginEntityListComponent<Subject> {
    
    @ViewChild('table') table: TableComponent;

    constructor(private subjectService: SubjectService) {       
        super('subject');
    }

    getEntities(): Promise<Subject[]> {
        return this.subjectService.getAll();
    }

    // Grid columns definition
    getColumnDefs(): any[] {
        function dateRenderer(date: number) {
            if (date) {
                return new Date(date).toLocaleDateString();
            }
            return null;
        };
        return [
            { headerName: "Common Name", field: "name", defaultSortCol: true, defaultAsc: true },
            { headerName: "Sex", field: "sex" },

            {
                headerName: "Birth Date", field: "birthDate", type: "date", cellRenderer: function (params: any) {
                    return dateRenderer(params.data.birthDate);
                }
            },
            { headerName: "Manual HD", field: "manualHemisphericDominance"},
            { headerName: "Language HD", field: "languageHemisphericDominance"},
            { headerName: "Imaged object category", field: "imagedObjectCategory"},
            { headerName: "Personal Comments", field: ""}
        ];
    }

    getCustomActionsDefs(): any[] {
        return [];
    }
}

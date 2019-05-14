import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

import { Subject } from '../shared/subject.model';
import { IdName } from '../../shared/models/id-name.model';
import { ImagesUrlUtil } from '../../shared/utils/images-url.util';
import { TreeNodeComponent } from '../../shared/components/tree/tree-node.component';
import { SubjectStudy } from '../shared/subject-study.model';
import { ExaminationService } from '../../examinations/shared/examination.service';
import { Examination } from '../../examinations/shared/examination.model';


@Component({
    selector: 'subject-tree',
    templateUrl: 'subject-tree.component.html',
    styleUrls: ['subject-tree.component.css'],
})

export class SubjectTreeComponent {

    constructor(private examinationService: ExaminationService, private router: Router) {
    }
   
    @Input() subject: Subject;
    @Input() studies: IdName[];
    public fileIconPath: string = ImagesUrlUtil.FILE_ICON_PATH;
    public folderIconPath: string = ImagesUrlUtil.FOLDER_12_ICON_PATH;
    private listIconPath: string = ImagesUrlUtil.LIST_ICON_PATH;
    private xRay2IconPath: string = ImagesUrlUtil.X_RAY_2_ICON_PATH;


    getStudyDetails(component: TreeNodeComponent) {
        component.dataLoading = true;
        let subjectStudy: SubjectStudy = component.nodeParams;
        this.examinationService.findExaminationsBySubjectAndStudy(subjectStudy.subject.id, subjectStudy.study.id)
            .then(examinations => {
                if (examinations) {
                    subjectStudy.examinations = examinations;
                    component.hasChildren = true;
                }
                component.open();
            })
    }

    showExaminationDetails(examinationId: number) {
        this.router.navigate(['/examination/details/' + examinationId])
    }

}
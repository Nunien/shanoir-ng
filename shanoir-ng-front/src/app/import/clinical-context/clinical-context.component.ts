import { Component, Output, EventEmitter, ViewChild, Input, OnChanges, SimpleChanges } from '@angular/core';
import { PatientDicom } from "./../dicom-data.model";
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { Study } from '../../studies/shared/study.model';
import { StudyService } from '../../studies/shared/study.service';
import { StudyCard } from '../../study-cards/shared/study-card.model';
import { ExaminationService } from '../../examinations/shared/examination.service';
import { Subject } from '../../subjects/shared/subject.model';
import { SubjectService } from "../../subjects/shared/subject.service";
import { SubjectWithSubjectStudy } from '../../subjects/shared/subject.with.subject-study.model';
import { SubjectExamination } from '../../examinations/shared/subject-examination.model';
import { IdNameObject } from '../../shared/models/id-name-object.model';
import { SubjectStudy } from '../../subjects/shared/subject-study.model';
import { Router } from '@angular/router';
import { AbstractImportStepComponent } from '../import-step.abstract';
import { slideDown } from '../../shared/animations/animations';
import { Examination } from '../../examinations/shared/examination.model';

export class ContextData {
    constructor(
        public study: Study,
        public studycard: StudyCard,
        public subject: SubjectWithSubjectStudy,
        public examination: SubjectExamination
    ) {};
}

@Component({
    selector: 'clinical-context',
    templateUrl: 'clinical-context.component.html',
    styleUrls: ['clinical-context.component.css', '../import.step.css'],
    animations: [slideDown]
})
export class ClinicalContextComponent extends AbstractImportStepComponent implements OnChanges {
    
    @Input() patient: PatientDicom;
    @Output() contextChange = new EventEmitter<ContextData>();
    
    @ViewChild('subjectCreationModal') subjectCreationModal: ModalComponent;
    @ViewChild('examCreationModal') examCreationModal: ModalComponent;

    private studycardMissingError: Boolean;
    private studycardNotCompatibleError: Boolean;

    private studies: Study[];
    private study: Study;
    private studycards: StudyCard[];
    private studycard: StudyCard;
    private subjects: SubjectWithSubjectStudy[]; 
    private subject: SubjectWithSubjectStudy;
    private subjectFromImport: Subject = new Subject();
    private examFromImport: Examination = new Examination();
    private examinations: SubjectExamination[];
    private examination: SubjectExamination;
    public niftiConverter: IdNameObject;
    
    constructor(
        private studyService: StudyService,
        private examinationService: ExaminationService,
        private subjectService: SubjectService,
        private router: Router,
    ) {
        super();
    }

    ngOnChanges(changes: SimpleChanges) {
        if(changes['patient'] && changes['patient'].currentValue) {
            this.studycard = null;
            this.subject = null;
            this.examination = null;
            this.onContextChange();
            this.fetchStudies();
        }
    }

    private fetchStudies(): void {
        this.studyService
            .findStudiesWithStudyCardsByUserAndEquipment(this.patient.studies[0].series[0].equipment)
            .then(studies => {
                this.studies = studies;
                this.prepareStudyStudycard(studies);
            }).catch(() => {
                this.studies = [];
            });
    }

    private prepareStudyStudycard(studies: Study[]): void {
        let compatibleStudies: Study[] = [];
        for (let study of studies) {
            if (study.compatible) {
                compatibleStudies.push(study);
            }
        }
        if (compatibleStudies.length == 1) {
            this.study = compatibleStudies[0];
            this.studycards = this.study.studyCards;
        }
    }
    
    private onSelectStudy(): void {
        this.studycards = null;
        this.studycard = null;
        this.subjects = null;
        this.subject = null;
        this.examinations = null;
        this.examination = null;
        if (this.study) {
            if (this.study.studyCards.length == 0) {
                this.studycardMissingError = true;
            } else {
                this.studycardMissingError = false;
                let compatibleStudycards: StudyCard[] = [];
                for (let studycard of this.study.studyCards) {
                    if (studycard.compatible) {
                        compatibleStudycards.push(studycard);
                    }
                }
                if (compatibleStudycards.length == 1) {
                    // autoselect studycard
                    this.studycard = compatibleStudycards[0];
                } 
                this.studycards = this.study.studyCards;
            }
        }
        this.onContextChange();
    }

    private onSelectStudycard(): void {
        if (this.studycard) {
            if (this.studycard.compatible) {
                this.studycardNotCompatibleError = false;
            } else {
                this.studycardNotCompatibleError = true;
            }
            this.niftiConverter = this.studycard.niftiConverter;
            this.studyService
                .findSubjectsByStudyId(this.study.id)
                .then(subjects => this.subjects = subjects);
        }
        this.onContextChange();
    }

    private onSelectSubject(): void {
        this.examinations = null;
        this.examination = null;
        if (this.subject) {
            this.examinationService
                .findExaminationsBySubjectAndStudy(this.subject.id, this.study.id)
                .then(examinations => this.examinations = examinations);
        }
        this.onContextChange();
    }

    private onSelectExamination() {
        this.onContextChange();
    }

    private onContextChange() {
        this.updateValidity();
        if (this.getValidity()) {
            this.contextChange.emit(this.getContext());
        }
    }
    
    private getContext(): ContextData {
        return new ContextData(this.study, this.studycard, this.subject, this.examination);
    }

    private updateSubjectStudyValues() {
        this.subjectService.updateSubjectStudyValues(this.subject.subjectStudy);
    }

    private initializePrefillSubject(): void {
        let subjectStudy = new SubjectStudy();
        subjectStudy.study = this.study;
        subjectStudy.physicallyInvolved = false;

        let newSubject = new Subject();
        newSubject.birthDate = this.patient.patientBirthDate;
        newSubject.name = this.patient.patientName;
        newSubject.sex = this.patient.patientSex; 
        newSubject.subjectStudyList = [subjectStudy];
        this.subjectFromImport = newSubject;
    }
    
    private onCloseSubjectPopin(subject?: Subject): void {
        if (subject) {
            // Add the subject to the select box and select it
            let subjectWithSubjectStudy = new SubjectWithSubjectStudy();
            subjectWithSubjectStudy.id = subject.id;
            subjectWithSubjectStudy.name = subject.name;
            subjectWithSubjectStudy.identifier = subject.identifier;
            subjectWithSubjectStudy.subjectStudy = subject.subjectStudyList[0];
            
            if (!this.subjects) {this.subjects = new Array<SubjectWithSubjectStudy>();}
            this.subjects.push(subjectWithSubjectStudy);
            this.subject = subjectWithSubjectStudy;
            this.onSelectSubject();
        }
        this.subjectCreationModal.hide();
    }

    private initializePrefillExam(): void {
        let newExam = new Examination();
        newExam.studyId = this.study.id;
        newExam.studyName = this.study.name;
        newExam.centerId = this.studycard.center.id;
        newExam.centerName = this.studycard.center.name;
        newExam.subjectId = this.subject.id;
        newExam.subjectName = this.subject.name;
        newExam.examinationDate = this.patient.studies[0].series[0].seriesDate;
        newExam.comment = this.patient.studies[0].studyDescription;

        this.examFromImport = newExam;
    }
    
    private onCloseExamPopin(examination?: Examination): void {
        if (examination) {
            // Add the new created exam to the select box and select it
            let subjectExam = new SubjectExamination();
            subjectExam.id = examination.id;
            subjectExam.examinationDate = examination.examinationDate;
            subjectExam.comment = examination.comment;

            if (!this.examinations) {this.examinations = new Array<SubjectExamination>();}
            this.examinations.push(subjectExam);
            this.examination = subjectExam;
            this.onSelectExamination();
        }
        this.examCreationModal.hide();
    }

    private showStudyDetails() {
        window.open('study?id=' + this.study.id + '&mode=view', '_blank');
    }

    private showSubjectDetails() {
        window.open('subject?id=' + this.subject.id + '&mode=view', '_blank');
    }

    private showStudyCardDetails() {
        window.open('studycard?id=' + this.studycard.id + '&mode=view', '_blank');
    }

    private showExaminationDetails() {
        window.open('examination?id=' + this.studycard.id + '&mode=view', '_blank');
    }

    getValidity(): boolean {
        let context = this.getContext();
        return (
            context.study != undefined && context.study != null
            && context.studycard != undefined && context.studycard != null
            && context.subject != undefined && context.subject != null
            && context.examination != undefined && context.examination != null
        );
    }
}
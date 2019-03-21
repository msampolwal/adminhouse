import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICalendarioComida } from 'app/shared/model/calendario-comida.model';
import { CalendarioComidaService } from './calendario-comida.service';

@Component({
    selector: 'jhi-calendario-comida-delete-dialog',
    templateUrl: './calendario-comida-delete-dialog.component.html'
})
export class CalendarioComidaDeleteDialogComponent {
    calendarioComida: ICalendarioComida;

    constructor(
        private calendarioComidaService: CalendarioComidaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.calendarioComidaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'calendarioComidaListModification',
                content: 'Deleted an calendarioComida'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-calendario-comida-delete-popup',
    template: ''
})
export class CalendarioComidaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ calendarioComida }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CalendarioComidaDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.calendarioComida = calendarioComida;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

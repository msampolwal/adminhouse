import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIngrediente } from 'app/shared/model/ingrediente.model';
import { IngredienteService } from './ingrediente.service';

@Component({
    selector: 'jhi-ingrediente-delete-dialog',
    templateUrl: './ingrediente-delete-dialog.component.html'
})
export class IngredienteDeleteDialogComponent {
    ingrediente: IIngrediente;

    constructor(
        private ingredienteService: IngredienteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ingredienteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'ingredienteListModification',
                content: 'Deleted an ingrediente'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-ingrediente-delete-popup',
    template: ''
})
export class IngredienteDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ingrediente }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IngredienteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.ingrediente = ingrediente;
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

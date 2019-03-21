import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrecioProducto } from 'app/shared/model/precio-producto.model';
import { PrecioProductoService } from './precio-producto.service';

@Component({
    selector: 'jhi-precio-producto-delete-dialog',
    templateUrl: './precio-producto-delete-dialog.component.html'
})
export class PrecioProductoDeleteDialogComponent {
    precioProducto: IPrecioProducto;

    constructor(
        private precioProductoService: PrecioProductoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.precioProductoService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'precioProductoListModification',
                content: 'Deleted an precioProducto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-precio-producto-delete-popup',
    template: ''
})
export class PrecioProductoDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ precioProducto }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PrecioProductoDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.precioProducto = precioProducto;
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

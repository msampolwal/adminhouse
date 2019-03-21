import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemDia } from 'app/shared/model/item-dia.model';
import { ItemDiaService } from './item-dia.service';

@Component({
    selector: 'jhi-item-dia-delete-dialog',
    templateUrl: './item-dia-delete-dialog.component.html'
})
export class ItemDiaDeleteDialogComponent {
    itemDia: IItemDia;

    constructor(private itemDiaService: ItemDiaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemDiaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'itemDiaListModification',
                content: 'Deleted an itemDia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-dia-delete-popup',
    template: ''
})
export class ItemDiaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ itemDia }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ItemDiaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.itemDia = itemDia;
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

import { element, by, promise, ElementFinder } from 'protractor';

export class PrecioProductoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-precio-producto div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PrecioProductoUpdatePage {
    pageTitle = element(by.id('jhi-precio-producto-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    precioInput = element(by.id('field_precio'));
    cantidadInput = element(by.id('field_cantidad'));
    productoSelect = element(by.id('field_producto'));
    unidadMedidaSelect = element(by.id('field_unidadMedida'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setPrecioInput(precio): promise.Promise<void> {
        return this.precioInput.sendKeys(precio);
    }

    getPrecioInput() {
        return this.precioInput.getAttribute('value');
    }

    setCantidadInput(cantidad): promise.Promise<void> {
        return this.cantidadInput.sendKeys(cantidad);
    }

    getCantidadInput() {
        return this.cantidadInput.getAttribute('value');
    }

    productoSelectLastOption(): promise.Promise<void> {
        return this.productoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    productoSelectOption(option): promise.Promise<void> {
        return this.productoSelect.sendKeys(option);
    }

    getProductoSelect(): ElementFinder {
        return this.productoSelect;
    }

    getProductoSelectedOption() {
        return this.productoSelect.element(by.css('option:checked')).getText();
    }

    unidadMedidaSelectLastOption(): promise.Promise<void> {
        return this.unidadMedidaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    unidadMedidaSelectOption(option): promise.Promise<void> {
        return this.unidadMedidaSelect.sendKeys(option);
    }

    getUnidadMedidaSelect(): ElementFinder {
        return this.unidadMedidaSelect;
    }

    getUnidadMedidaSelectedOption() {
        return this.unidadMedidaSelect.element(by.css('option:checked')).getText();
    }

    save(): promise.Promise<void> {
        return this.saveButton.click();
    }

    cancel(): promise.Promise<void> {
        return this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

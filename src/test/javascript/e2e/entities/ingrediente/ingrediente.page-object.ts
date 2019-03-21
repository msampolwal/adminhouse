import { element, by, promise, ElementFinder } from 'protractor';

export class IngredienteComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-ingrediente div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class IngredienteUpdatePage {
    pageTitle = element(by.id('jhi-ingrediente-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    cantidadInput = element(by.id('field_cantidad'));
    comidaSelect = element(by.id('field_comida'));
    productoSelect = element(by.id('field_producto'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setCantidadInput(cantidad): promise.Promise<void> {
        return this.cantidadInput.sendKeys(cantidad);
    }

    getCantidadInput() {
        return this.cantidadInput.getAttribute('value');
    }

    comidaSelectLastOption(): promise.Promise<void> {
        return this.comidaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    comidaSelectOption(option): promise.Promise<void> {
        return this.comidaSelect.sendKeys(option);
    }

    getComidaSelect(): ElementFinder {
        return this.comidaSelect;
    }

    getComidaSelectedOption() {
        return this.comidaSelect.element(by.css('option:checked')).getText();
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

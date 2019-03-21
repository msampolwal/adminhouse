import { element, by, promise, ElementFinder } from 'protractor';

export class ProductoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-producto div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ProductoUpdatePage {
    pageTitle = element(by.id('jhi-producto-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));
    unidadMedidaSelect = element(by.id('field_unidadMedida'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNombreInput(nombre): promise.Promise<void> {
        return this.nombreInput.sendKeys(nombre);
    }

    getNombreInput() {
        return this.nombreInput.getAttribute('value');
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

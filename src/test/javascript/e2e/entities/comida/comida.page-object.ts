import { element, by, promise, ElementFinder } from 'protractor';

export class ComidaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-comida div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ComidaUpdatePage {
    pageTitle = element(by.id('jhi-comida-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nombreInput = element(by.id('field_nombre'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setNombreInput(nombre): promise.Promise<void> {
        return this.nombreInput.sendKeys(nombre);
    }

    getNombreInput() {
        return this.nombreInput.getAttribute('value');
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

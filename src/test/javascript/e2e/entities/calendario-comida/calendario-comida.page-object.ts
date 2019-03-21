import { element, by, promise, ElementFinder } from 'protractor';

export class CalendarioComidaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-calendario-comida div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CalendarioComidaUpdatePage {
    pageTitle = element(by.id('jhi-calendario-comida-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    fechaInput = element(by.id('field_fecha'));
    grupoSelect = element(by.id('field_grupo'));
    diaSelect = element(by.id('field_dia'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setFechaInput(fecha): promise.Promise<void> {
        return this.fechaInput.sendKeys(fecha);
    }

    getFechaInput() {
        return this.fechaInput.getAttribute('value');
    }

    grupoSelectLastOption(): promise.Promise<void> {
        return this.grupoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    grupoSelectOption(option): promise.Promise<void> {
        return this.grupoSelect.sendKeys(option);
    }

    getGrupoSelect(): ElementFinder {
        return this.grupoSelect;
    }

    getGrupoSelectedOption() {
        return this.grupoSelect.element(by.css('option:checked')).getText();
    }

    diaSelectLastOption(): promise.Promise<void> {
        return this.diaSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    diaSelectOption(option): promise.Promise<void> {
        return this.diaSelect.sendKeys(option);
    }

    getDiaSelect(): ElementFinder {
        return this.diaSelect;
    }

    getDiaSelectedOption() {
        return this.diaSelect.element(by.css('option:checked')).getText();
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

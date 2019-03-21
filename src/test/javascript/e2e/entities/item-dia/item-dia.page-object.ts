import { element, by, promise, ElementFinder } from 'protractor';

export class ItemDiaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-item-dia div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ItemDiaUpdatePage {
    pageTitle = element(by.id('jhi-item-dia-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    tipoSelect = element(by.id('field_tipo'));
    diaSelect = element(by.id('field_dia'));
    comidaSelect = element(by.id('field_comida'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setTipoSelect(tipo): promise.Promise<void> {
        return this.tipoSelect.sendKeys(tipo);
    }

    getTipoSelect() {
        return this.tipoSelect.element(by.css('option:checked')).getText();
    }

    tipoSelectLastOption(): promise.Promise<void> {
        return this.tipoSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

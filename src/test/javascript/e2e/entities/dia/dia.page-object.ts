import { element, by, promise, ElementFinder } from 'protractor';

export class DiaComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-dia div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class DiaUpdatePage {
    pageTitle = element(by.id('jhi-dia-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    diaSemanaSelect = element(by.id('field_diaSemana'));
    grupoSelect = element(by.id('field_grupo'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    setDiaSemanaSelect(diaSemana): promise.Promise<void> {
        return this.diaSemanaSelect.sendKeys(diaSemana);
    }

    getDiaSemanaSelect() {
        return this.diaSemanaSelect.element(by.css('option:checked')).getText();
    }

    diaSemanaSelectLastOption(): promise.Promise<void> {
        return this.diaSemanaSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

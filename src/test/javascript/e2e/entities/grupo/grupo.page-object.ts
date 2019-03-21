import { element, by, promise, ElementFinder } from 'protractor';

export class GrupoComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    title = element.all(by.css('jhi-grupo div h2#page-heading span')).first();

    clickOnCreateButton(): promise.Promise<void> {
        return this.createButton.click();
    }

    getTitle(): any {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GrupoUpdatePage {
    pageTitle = element(by.id('jhi-grupo-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    usersSelect = element(by.id('field_users'));

    getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    usersSelectLastOption(): promise.Promise<void> {
        return this.usersSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    usersSelectOption(option): promise.Promise<void> {
        return this.usersSelect.sendKeys(option);
    }

    getUsersSelect(): ElementFinder {
        return this.usersSelect;
    }

    getUsersSelectedOption() {
        return this.usersSelect.element(by.css('option:checked')).getText();
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

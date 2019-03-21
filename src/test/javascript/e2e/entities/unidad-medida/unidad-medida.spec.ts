import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { UnidadMedidaComponentsPage, UnidadMedidaUpdatePage } from './unidad-medida.page-object';

describe('UnidadMedida e2e test', () => {
    let navBarPage: NavBarPage;
    let unidadMedidaUpdatePage: UnidadMedidaUpdatePage;
    let unidadMedidaComponentsPage: UnidadMedidaComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load UnidadMedidas', () => {
        navBarPage.goToEntity('unidad-medida');
        unidadMedidaComponentsPage = new UnidadMedidaComponentsPage();
        expect(unidadMedidaComponentsPage.getTitle()).toMatch(/adminhouseApp.unidadMedida.home.title/);
    });

    it('should load create UnidadMedida page', () => {
        unidadMedidaComponentsPage.clickOnCreateButton();
        unidadMedidaUpdatePage = new UnidadMedidaUpdatePage();
        expect(unidadMedidaUpdatePage.getPageTitle()).toMatch(/adminhouseApp.unidadMedida.home.createOrEditLabel/);
        unidadMedidaUpdatePage.cancel();
    });

    it('should create and save UnidadMedidas', () => {
        unidadMedidaComponentsPage.clickOnCreateButton();
        unidadMedidaUpdatePage.setNombreInput('nombre');
        expect(unidadMedidaUpdatePage.getNombreInput()).toMatch('nombre');
        unidadMedidaUpdatePage.save();
        expect(unidadMedidaUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

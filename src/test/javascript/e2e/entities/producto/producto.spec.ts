import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ProductoComponentsPage, ProductoUpdatePage } from './producto.page-object';

describe('Producto e2e test', () => {
    let navBarPage: NavBarPage;
    let productoUpdatePage: ProductoUpdatePage;
    let productoComponentsPage: ProductoComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Productos', () => {
        navBarPage.goToEntity('producto');
        productoComponentsPage = new ProductoComponentsPage();
        expect(productoComponentsPage.getTitle()).toMatch(/adminhouseApp.producto.home.title/);
    });

    it('should load create Producto page', () => {
        productoComponentsPage.clickOnCreateButton();
        productoUpdatePage = new ProductoUpdatePage();
        expect(productoUpdatePage.getPageTitle()).toMatch(/adminhouseApp.producto.home.createOrEditLabel/);
        productoUpdatePage.cancel();
    });

    it('should create and save Productos', () => {
        productoComponentsPage.clickOnCreateButton();
        productoUpdatePage.setNombreInput('nombre');
        expect(productoUpdatePage.getNombreInput()).toMatch('nombre');
        productoUpdatePage.unidadMedidaSelectLastOption();
        productoUpdatePage.save();
        expect(productoUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

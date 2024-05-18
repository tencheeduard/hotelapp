import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffCanvasComponent } from './offcanvas.component';

describe('OffcanvasComponent', () => {
  let component: OffCanvasComponent;
  let fixture: ComponentFixture<OffCanvasComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OffCanvasComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(OffCanvasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

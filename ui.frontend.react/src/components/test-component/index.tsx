import React from "react";
import MysiteBaseModel from "../../base/mysite-base-model";
// @ts-ignore
import { createCustomElement, DOMModel, byAttrVal } from "@adobe/react-webcomponent";
import "./test-component.scss";

class TestComponentModel extends DOMModel implements MysiteBaseModel {
  @byAttrVal nameprop?: string;
  hidePlaceHolder = false;
}

const TestComponent = (props: TestComponentModel) => {
    const {nameprop}=props;
  return (
    <div className="test-component">
      {`hello ${nameprop}, welcome to my site test component`}
    </div>
  );
};

const TestComponentElement = createCustomElement(
  TestComponent,
  TestComponentModel,
  "element"
);

// @ts-ignore
window.customElements.define("test-component", TestComponentElement);

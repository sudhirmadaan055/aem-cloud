import React from "react";
import "./test-component.scss";

interface TestComponentProps {
  nameprop?: string;
}

const TestComponent: React.FC<TestComponentProps> = ({ nameprop = "World" }) => {
  return (
    <div className="test-component">
      {`hello ${nameprop}, welcome to my site test component`}
    </div>
  );
};

export default TestComponent;

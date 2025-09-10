import React from 'react';
import MysiteBaseModel from '../../base/mysite-base-model';
// @ts-ignore
import { createCustomElement, DOMModel, byAttrVal } from '@adobe/react-webcomponent';
import './listing.scss';
import { Listing } from '../../Atomic';

class ListingComponentModel extends DOMModel implements MysiteBaseModel {
  @byAttrVal aemendpoint?: string;
  hidePlaceHolder = false;
}

const ListingComponent = (props: ListingComponentModel) => {
  const { aemendpoint } = props;


  return (
    <Listing
      title={"Title"}
      columns={3}
      aemEndpoint={""}
    />
  );
};

const ListingWebComponent = createCustomElement(
  ListingComponent,
  ListingComponentModel,
  'element'
);

// @ts-ignore
window.customElements.define('listing-component', ListingWebComponent);

// export default function Example() {
//   return (
//     <listing-component
//       title="Featured Products"
//       columns="3"
//       // aemendpoint="/content/your-site/your-page/jcr:content/your-component.model.json"
//     />
//   );
// }
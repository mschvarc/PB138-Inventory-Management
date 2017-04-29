import React, { Component } from 'react';

class Inventory extends Component {
	render() {
		return <div className="page-inventory row">
      <div className="small-12 columns">
        <h2>Current Inventory</h2>

        <table className="dashboard-table">
          <colgroup>
            <col width="150" />
            <col width="80" />
            <col width="200" />
            <col width="60" />
            <col width="220" />
            <col width="170" />
          </colgroup>
          <thead>
            <tr>
              <th><a href="#">Id<i className="fa fa-caret-down"></i></a></th>
              <th><a href="#">Name<i className="fa fa-caret-down"></i></a></th>
              <th><a href="#">Status<i className="fa fa-caret-down"></i></a></th>
              <th><a href="#">Column 4 <i className="fa fa-caret-down"></i></a></th>
              <th><a href="#">Column 5 <i className="fa fa-caret-down"></i></a></th>
              <th><a href="#">Column 6 <i className="fa fa-caret-down"></i></a></th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                <div className="flex-container align-justify align-top">
                  <div className="flex-child-shrink">
                    <img className="dashboard-table-image" src="http://lorempixel.com/50/50/people/" />
                  </div>
                  <div className="flex-child-grow">
                    <h6 className="dashboard-table-text">A Person</h6>
                    <span className="dashboard-table-timestamp">03/04/2017</span>
                  </div>
                </div>
              </td>
              <td>Single Line</td>
              <td className="bold">Name</td>
              <td>A Date</td>
              <td>
                <div className="flex-container align-top">
                  <div className="flex-child-shrink">
                    <img className="dashboard-table-image" src="http://lorempixel.com/50/50/people/" />
                  </div>
                  <div className="flex-child">
                    <h6 className="dashboard-table-text">Another person did something</h6>
                    <span className="dashboard-table-timestamp">03/08/2017</span>
                  </div>
                </div>
              </td>
              <td className="listing-inquiry-status">

                <div className="flex-container align-top">
                  <div className="flex-child-shrink">
                    <img className="dashboard-table-image" src="http://lorempixel.com/25/25/abstract/" />
                  </div>
                  <div className="flex-child">
                    <h6 className="dashboard-table-text"><a href="#">A longer wrapping item with an image that is aligned to the top</a></h6>
                  </div>
                </div>

              </td>
            </tr>
            <tr>
              <td>
                <div className="flex-container align-justify align-top">
                  <div className="flex-child-shrink">
                    <img className="dashboard-table-image" src="http://lorempixel.com/50/50/people/" />
                  </div>
                  <div className="flex-child-grow">
                    <h6 className="dashboard-table-text">A Person</h6>
                    <span className="dashboard-table-timestamp">03/04/2017</span>
                  </div>
                </div>
              </td>
              <td>Single Line</td>
              <td className="bold">A Bold Line</td>
              <td>A Date</td>
              <td>
                <div className="flex-container align-top">
                  <div className="flex-child-shrink">
                    <img className="dashboard-table-image" src="http://lorempixel.com/50/50/people/" />
                  </div>
                  <div className="flex-child">
                    <h6 className="dashboard-table-text">Another person did something</h6>
                    <span className="dashboard-table-timestamp">03/08/2017</span>
                  </div>
                </div>
              </td>
              <td className="listing-inquiry-status">

                <div className="flex-container align-top">
                  <div className="flex-child-shrink">
                    <img className="dashboard-table-image" src="http://lorempixel.com/25/25/abstract/" />
                  </div>
                  <div className="flex-child">
                    <h6 className="dashboard-table-text"><a href="#">A longer wrapping item with an image that is aligned to the top</a></h6>
                  </div>
                </div>

              </td>
            </tr>
          </tbody>
        </table>

      </div>
    </div>
	}
}

export default Inventory;

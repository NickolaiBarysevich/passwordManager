import React from "react";
import Cookies from 'js-cookie';
import './Navigation.css'

const Navigation = (props) => (
    <div className='navigation-container'>
        {
            Cookies.get('Authenticated')
            && <div className='btn-group'>
                <div>
                    <button className='btn' onClick={props.toggleAddForm}>
                        {
                            !props.showAddForm
                                ? 'Add credentials'
                                : 'Close form'
                        }
                    </button>
                </div>
                <div>
                    <a href='/logout' className='btn'>Logout</a>
                </div>
            </div>
        }
    </div>
);

export default Navigation;
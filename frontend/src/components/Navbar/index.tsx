import {ReactComponent as GithubIcon } from 'assets/img/github.svg';
import './styles.css'

function Navbar() {
    return (
      <header>
        <nav className="container">
          <div className="library-nav-content">
            <h1>Library</h1>
            <a href="https://github.com/rtrnonato">
              <div className="library-contact-container">
                <GithubIcon/>
                <p className="library-contact-link">/rtrnonato</p>
              </div>
            </a>
          </div>
        </nav>
      </header>
    );
}

export default Navbar;
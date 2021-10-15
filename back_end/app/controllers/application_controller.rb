class ApplicationController < ActionController::Base
  skip_before_action :verify_authenticity_token, if: :devise_controller?
  respond_to :html, :json
end

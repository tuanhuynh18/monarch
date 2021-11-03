class AccommodationsController < ApplicationController
  before_action :set_accommodation, only: :show

  # GET /accommodations or /accommodations.json
  def index
    @accommodations = Accommodation.all
  end

  # GET /accommodations/1 or /accommodations/1.json
  def show
  end

  private
  # Use callbacks to share common setup or constraints between actions.
  def set_accommodation
    @accommodation = Accommodation.find(params[:id])
  end
end
